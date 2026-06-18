/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.accounting.spotrate.service;

import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.spotrate.api.DailySpotRateJsonInputParams;
import org.apache.fineract.accounting.spotrate.command.DailySpotRateCommand;
import org.apache.fineract.accounting.spotrate.domain.DailySpotRate;
import org.apache.fineract.accounting.spotrate.domain.DailySpotRateRepository;
import org.apache.fineract.accounting.spotrate.domain.DailySpotRateRepositoryWrapper;
import org.apache.fineract.accounting.spotrate.exception.DailySpotRateDuplicateException;
import org.apache.fineract.accounting.spotrate.serialization.DailySpotRateCommandFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DailySpotRateWritePlatformServiceJpaRepositoryImpl implements DailySpotRateWritePlatformService {

    private static final Logger LOG = LoggerFactory.getLogger(DailySpotRateWritePlatformServiceJpaRepositoryImpl.class);

    private final DailySpotRateRepository dailySpotRateRepository;
    private final DailySpotRateRepositoryWrapper dailySpotRateRepositoryWrapper;
    private final DailySpotRateCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    private final OfficeRepositoryWrapper officeRepositoryWrapper;

    @Transactional
    @Override
    public CommandProcessingResult createDailySpotRate(final JsonCommand command) {
        try {
            final DailySpotRateCommand spotRateCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
            spotRateCommand.validateForCreate();

            final Long officeId = command.longValueOfParameterNamed(DailySpotRateJsonInputParams.OFFICE_ID.getValue());
            final Office office = this.officeRepositoryWrapper.findOneWithNotFoundDetection(officeId);

            final String currencyCode = command.stringValueOfParameterNamed(DailySpotRateJsonInputParams.CURRENCY_CODE.getValue());
            final LocalDate rateDate = command.localDateValueOfParameterNamed(DailySpotRateJsonInputParams.RATE_DATE.getValue());

            // enforce unique constraint at service level for a clear error message
            this.dailySpotRateRepository.findByOfficeIdAndCurrencyCodeAndRateDate(officeId, currencyCode, rateDate).ifPresent(existing -> {
                throw new DailySpotRateDuplicateException(officeId, currencyCode, rateDate.toString());
            });

            final DailySpotRate dailySpotRate = DailySpotRate.fromJson(office, command);
            this.dailySpotRateRepository.saveAndFlush(dailySpotRate);

            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(dailySpotRate.getId())
                    .withOfficeId(officeId).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(throwable, dve);
            return CommandProcessingResult.empty();
        }
    }

    @Transactional
    @Override
    public CommandProcessingResult updateDailySpotRate(final Long dailySpotRateId, final JsonCommand command) {
        try {
            final DailySpotRateCommand spotRateCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
            spotRateCommand.validateForUpdate();

            final DailySpotRate dailySpotRate = this.dailySpotRateRepositoryWrapper.findOneWithNotFoundDetection(dailySpotRateId);

            final Map<String, Object> changesOnly = dailySpotRate.update(command);

            if (!changesOnly.isEmpty()) {
                this.dailySpotRateRepository.saveAndFlush(dailySpotRate);
            }

            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(dailySpotRateId).with(changesOnly)
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(throwable, dve);
            return CommandProcessingResult.empty();
        }
    }

    private void handleDataIntegrityIssues(final Throwable realCause, final NonTransientDataAccessException dve) {
        if (realCause.getMessage().contains("uq_daily_spot_rate_office_currency_date")) {
            throw new PlatformDataIntegrityException("error.msg.dailyspotrate.duplicate",
                    "A Daily Spot Rate for this office, currency and date already exists.");
        }
        LOG.error("Error occurred.", dve);
        throw new PlatformDataIntegrityException("error.msg.dailyspotrate.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource Daily Spot Rate: " + realCause.getMessage());
    }
}
