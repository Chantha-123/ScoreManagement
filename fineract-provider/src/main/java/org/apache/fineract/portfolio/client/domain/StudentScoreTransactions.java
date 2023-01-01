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
package org.apache.fineract.portfolio.client.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "m_student_score_transaction")
public class StudentScoreTransactions extends AbstractPersistableCustom {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @Column(name = "studyear_id", nullable = false)
    private Long stuedyerar_id;

    @Column(name = "transactiondate")
    private LocalDate transactiondate;

    @Column(name = "class_id")
    private Long class_id;
    
    
    @Column(name = "score_type_id")
    private Long score_type_id;
    
    @Column(name = "subject_id")
    private Long subject_id;
    
    
    @Column(name = "score_amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal score_amount;

   
    @Column(name = "is_completed", nullable = false)
    private boolean is_completed = false;
    
    @Column(name = "monthly_id")
    private Long monthly_id;
    

    
    
    protected StudentScoreTransactions() {
        super();
    }



	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public Long getStuedyerar_id() {
		return stuedyerar_id;
	}



	public void setStuedyerar_id(Long stuedyerar_id) {
		this.stuedyerar_id = stuedyerar_id;
	}



	public LocalDate getTransactiondate() {
		return transactiondate;
	}



	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}



	public Long getClass_id() {
		return class_id;
	}



	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}



	public Long getScore_type_id() {
		return score_type_id;
	}



	public void setScore_type_id(Long score_type_id) {
		this.score_type_id = score_type_id;
	}



	public Long getSubject_id() {
		return subject_id;
	}



	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}



	public BigDecimal getScore_amount() {
		return score_amount;
	}



	public void setScore_amount(BigDecimal score_amount) {
		this.score_amount = score_amount;
	}



	public boolean isIs_completed() {
		return is_completed;
	}



	public void setIs_completed(boolean is_completed) {
		this.is_completed = is_completed;
	}



	public Long getMonthly_id() {
		return monthly_id;
	}



	public void setMonthly_id(Long monthly_id) {
		this.monthly_id = monthly_id;
	}



	public StudentScoreTransactions(Client client, Long stuedyerar_id, LocalDate transactiondate, Long class_id,
			Long score_type_id, Long subject_id, BigDecimal score_amount, boolean is_completed, Long monthly_id) {
		super();
		this.client = client;
		this.stuedyerar_id = stuedyerar_id;
		this.transactiondate = transactiondate;
		this.class_id = class_id;
		this.score_type_id = score_type_id;
		this.subject_id = subject_id;
		this.score_amount = score_amount;
		this.is_completed = is_completed;
		this.monthly_id = monthly_id;
	}



	

  

}
