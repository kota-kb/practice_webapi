package practice.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import practice.datamock.DataMock;
import practice.datamock.UUIDFactory;
import practice.dto.DetailDto;
import practice.dto.DetailDto.Category;

/**
 * 明細処理サービス
 */
@Component
public class DetailsService {

    static final Logger logger = LoggerFactory.getLogger(DetailsService.class);

    @Autowired
    DataMock dataMock;

    @Autowired
    UUIDFactory uuid;

    /**
     * 明細条件検索
     *
     * @param year 対象年
     * @param month 対象月
     * @return
     */
    public List<DetailDto> findDetailsByYearMonth(int year, int month) {
        return dataMock.findByYearMonth("test1", year, month);
    }

    /**
     * 明細一意検索
     *
     * @param id 明細ID
     * @return
     */
    public Optional<DetailDto> findDetailById(String id) {
        return Optional.ofNullable(dataMock.findById("test1", id));
    }

    /**
     * 明細追加
     *
     * @param dto 明細情報
     * @return
     */
    public DetailDto addDetail(DetailDto dto) {
        return dataMock.add("test1", DetailDto.create(uuid.getNewId(), dto));
    }

    /**
     * 明細更新
     *
     * @param id 明細ID
     * @param dto 明細情報
     * @return
     */
    public DetailDto updateDetail(DetailDto dto) {
        return dataMock.mod("test1", dto);
    }

    /**
     * @return
     */
    public Map<String, List<Category>> getCategoryMap() {
        return dataMock.getCategoryMap();
    }

}
