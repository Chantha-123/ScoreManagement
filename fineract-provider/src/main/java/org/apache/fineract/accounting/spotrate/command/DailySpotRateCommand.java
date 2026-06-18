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
package org.apache.fineract.accounting.spotrate.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.spotrate.api.DailySpotRateJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;

/**
 * Immutable command for creating/updating a Daily Spot Rate.
 */
@RequiredArgsConstructor
@Getter
public class DailySpotRateCommand {

    private final Long id;
    private final Long officeId;
    private final String currencyCode;
    private final LocalDate rateDate;
    private final BigDecimal spotRate;
    private final BigDecimal buyingRate;
    private final BigDecimal sellingRate;

    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("DailySpotRate");

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.OFFICE_ID.getValue()).value(this.officeId).notNull()
                .longGreaterThanZero();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.CURRENCY_CODE.getValue()).value(this.currencyCode).notBlank()
                .notExceedingLengthOf(3);

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.RATE_DATE.getValue()).value(this.rateDate).notNull();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.SPOT_RATE.getValue()).value(this.spotRate).notNull()
                .positiveAmount();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.BUYING_RATE.getValue()).value(this.buyingRate).notNull()
                .positiveAmount();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.SELLING_RATE.getValue()).value(this.sellingRate).notNull()
                .positiveAmount();

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }

    public void validateForUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("DailySpotRate");

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.SPOT_RATE.getValue()).value(this.spotRate).ignoreIfNull()
                .positiveAmount();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.BUYING_RATE.getValue()).value(this.buyingRate).ignoreIfNull()
                .positiveAmount();

        baseDataValidator.reset().parameter(DailySpotRateJsonInputParams.SELLING_RATE.getValue()).value(this.sellingRate).ignoreIfNull()
                .positiveAmount();

        baseDataValidator.reset().anyOfNotNull(this.spotRate, this.buyingRate, this.sellingRate);

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
