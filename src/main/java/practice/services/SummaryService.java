package practice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import practice.datamock.DataMock;
import practice.dto.SummaryDto;

@Component
public class SummaryService {

    static final Logger logger = LoggerFactory.getLogger(SummaryService.class);

    @Autowired
    DataMock dataMock;

    /**
     * サマリ情報検索
     *
     * @param year 対象年
     * @param month 対象月
     * @return
     */
    public Optional<SummaryDto> findSummaryByYearMonth(int year, int month) {
        SummaryDto dto = dataMock.getSummary("test1", year, month);
        return Optional.ofNullable(dto);
    }

}
