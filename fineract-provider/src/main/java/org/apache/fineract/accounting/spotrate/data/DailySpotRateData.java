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
package org.apache.fineract.accounting.spotrate.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Immutable object representing a Daily Spot Rate.
 *
 * Note: no getter/setters required as google-gson will produce json from fields of object.
 */
public class DailySpotRateData implements Serializable {

    private final Long id;
    private final Long officeId;
    private final String officeName;
    private final String currencyCode;
    private final LocalDate rateDate;
    private final BigDecimal spotRate;
    private final BigDecimal buyingRate;
    private final BigDecimal sellingRate;

    public DailySpotRateData(final Long id, final Long officeId, final String officeName, final String currencyCode,
            final LocalDate rateDate, final BigDecimal spotRate, final BigDecimal buyingRate, final BigDecimal sellingRate) {
        this.id = id;
        this.officeId = officeId;
        this.officeName = officeName;
        this.currencyCode = currencyCode;
        this.rateDate = rateDate;
        this.spotRate = spotRate;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }

    public Long getId() {
        return this.id;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public LocalDate getRateDate() {
        return this.rateDate;
    }

    public BigDecimal getSpotRate() {
        return this.spotRate;
    }

    public BigDecimal getBuyingRate() {
        return this.buyingRate;
    }

    public BigDecimal getSellingRate() {
        return this.sellingRate;
    }
}
