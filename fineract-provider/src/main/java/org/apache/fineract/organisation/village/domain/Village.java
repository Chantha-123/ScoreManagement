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
package org.apache.fineract.organisation.village.domain;

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
import org.apache.fineract.organisation.commune.domain.Commune;
import org.apache.fineract.organisation.village.api.VillageJsonInputParams;

@Entity
@Table(name = "m_village", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "commune_id", "code" }, name = "uq_village_commune_code") })
public class Village extends AbstractPersistableCustom {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id", nullable = false)
    private Commune commune;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "name_khm", nullable = false, length = 100)
    private String nameKhm;

    @Column(name = "name_eng", nullable = false, length = 100)
    private String nameEng;

    protected Village() {}

    private Village(final Commune commune, final String code, final String nameKhm, final String nameEng) {
        this.commune = commune;
        this.code = code;
        this.nameKhm = nameKhm;
        this.nameEng = nameEng;
    }

    public static Village fromJson(final Commune commune, final JsonCommand command) {
        final String code = command.stringValueOfParameterNamed(VillageJsonInputParams.CODE.getValue());
        final String nameKhm = command.stringValueOfParameterNamed(VillageJsonInputParams.NAME_KHM.getValue());
        final String nameEng = command.stringValueOfParameterNamed(VillageJsonInputParams.NAME_ENG.getValue());
        return new Village(commune, code, nameKhm, nameEng);
    }

    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>();

        if (command.isChangeInStringParameterNamed(VillageJsonInputParams.CODE.getValue(), this.code)) {
            final String newValue = command.stringValueOfParameterNamed(VillageJsonInputParams.CODE.getValue());
            actualChanges.put(VillageJsonInputParams.CODE.getValue(), newValue);
            this.code = newValue;
        }

        if (command.isChangeInStringParameterNamed(VillageJsonInputParams.NAME_KHM.getValue(), this.nameKhm)) {
            final String newValue = command.stringValueOfParameterNamed(VillageJsonInputParams.NAME_KHM.getValue());
            actualChanges.put(VillageJsonInputParams.NAME_KHM.getValue(), newValue);
            this.nameKhm = newValue;
        }

        if (command.isChangeInStringParameterNamed(VillageJsonInputParams.NAME_ENG.getValue(), this.nameEng)) {
            final String newValue = command.stringValueOfParameterNamed(VillageJsonInputParams.NAME_ENG.getValue());
            actualChanges.put(VillageJsonInputParams.NAME_ENG.getValue(), newValue);
            this.nameEng = newValue;
        }

        return actualChanges;
    }

    public Commune getCommune() {
        return this.commune;
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
