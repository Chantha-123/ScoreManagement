package org.apache.fineract.infrastructure.businessdate.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.apache.fineract.infrastructure.businessdate.data.BusinessDateData;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDate;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-15T22:26:38+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240103-0614, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class BusinessDateMapperImpl implements BusinessDateMapper {

    @Override
    public BusinessDateData map(BusinessDate source) {
        if ( source == null ) {
            return null;
        }

        BusinessDateType businessDateType = null;
        LocalDate date = null;

        businessDateType = source.getType();
        date = source.getDate();

        BusinessDateData businessDateData = new BusinessDateData( businessDateType, date );

        return businessDateData;
    }

    @Override
    public List<BusinessDateData> map(List<BusinessDate> sources) {
        if ( sources == null ) {
            return null;
        }

        List<BusinessDateData> list = new ArrayList<BusinessDateData>( sources.size() );
        for ( BusinessDate businessDate : sources ) {
            list.add( map( businessDate ) );
        }

        return list;
    }
}
