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
package org.apache.fineract.organisation.district.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.district.api.DistrictJsonInputParams;
import org.apache.fineract.organisation.province.domain.Province;

@Entity
@Table(name = "m_district", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "province_id", "code" }, name = "uq_district_province_code") })
public class District extends AbstractPersistableCustom {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "name_khm", nullable = false, length = 100)
    private String nameKhm;

    @Column(name = "name_eng", nullable = false, length = 100)
    private String nameEng;

    protected District() {}

    private District(final Province province, final String code, final String nameKhm, final String nameEng) {
        this.province = province;
        this.code = code;
        this.nameKhm = nameKhm;
        this.nameEng = nameEng;
    }

    public static District fromJson(final Province province, final JsonCommand command) {
        final String code = command.stringValueOfParameterNamed(DistrictJsonInputParams.CODE.getValue());
        final String nameKhm = command.stringValueOfParameterNamed(DistrictJsonInputParams.NAME_KHM.getValue());
        final String nameEng = command.stringValueOfParameterNamed(DistrictJsonInputParams.NAME_ENG.getValue());
        return new District(province, code, nameKhm, nameEng);
    }

    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>();

        if (command.isChangeInStringParameterNamed(DistrictJsonInputParams.CODE.getValue(), this.code)) {
            final String newValue = command.stringValueOfParameterNamed(DistrictJsonInputParams.CODE.getValue());
            actualChanges.put(DistrictJsonInputParams.CODE.getValue(), newValue);
            this.code = newValue;
        }

        if (command.isChangeInStringParameterNamed(DistrictJsonInputParams.NAME_KHM.getValue(), this.nameKhm)) {
            final String newValue = command.stringValueOfParameterNamed(DistrictJsonInputParams.NAME_KHM.getValue());
            actualChanges.put(DistrictJsonInputParams.NAME_KHM.getValue(), newValue);
            this.nameKhm = newValue;
        }

        if (command.isChangeInStringParameterNamed(DistrictJsonInputParams.NAME_ENG.getValue(), this.nameEng)) {
            final String newValue = command.stringValueOfParameterNamed(DistrictJsonInputParams.NAME_ENG.getValue());
            actualChanges.put(DistrictJsonInputParams.NAME_ENG.getValue(), newValue);
            this.nameEng = newValue;
        }

        return actualChanges;
    }

    public Province getProvince() {
        return this.province;
    }

    public String getCode() {
        return this.code;
    }

    public String getNameKhm() {
        return this.nameKhm;
    }

    public String getNameEng() {
        return this.nameEng;
    }
}
