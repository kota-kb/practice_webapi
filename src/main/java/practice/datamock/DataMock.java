package practice.datamock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import practice.dto.DetailDto;
import practice.dto.DetailDto.Category;
import practice.dto.DetailDto.TypeDto;
import practice.dto.SummaryDto;

@Component
public class DataMock {

    static final Logger logger = LoggerFactory.getLogger(DataMock.class);

    @Autowired
    UUIDFactory uuid;

    static final DateTimeFormatter ftr = DateTimeFormatter.ofPattern("YYYYMM");

    private Map<String, Map<String, DetailDto>> map;




    /**
     * コンストラクタ実行後処理
     */
    @PostConstruct
    public void populateCache() {

        // データモックにダミーデータを投入
        map = new HashMap<>();

        map.put("test1", new HashMap<>());
        map.put("test2", new HashMap<>());

        Map<String, DetailDto> test1 = map.get("test1");
        String id = uuid.getNewId();
        test1.put(id, DetailDto.create(id, LocalDate.of(2020, 4, 4), TypeDto.INCOME, Category.of("2", "収入A"), 150000L, "備考1"));
        id = uuid.getNewId();
        test1.put(id, DetailDto.create(id, LocalDate.of(2020, 4, 7), TypeDto.EXPENSE, Category.of("6", "支出C"), 38000L, "備考2"));
        id = uuid.getNewId();
        test1.put(id, DetailDto.create(id, LocalDate.of(2020, 4, 12), TypeDto.EXPENSE, Category.of("7", "支出D"), 23000L, "備考3"));

        logger.debug("map:size:" + map.get("test1").size());

    }

    /**
     * @param user
     * @param year
     * @param month
     * @return
     */
    public List<DetailDto> findByYearMonth(String user, int year, int month) {

        Map<String, DetailDto> a = map.get(user);

        // 日付で絞り込み～日付/ID(逆順)でソート
        return a.values().stream()
                .filter(d -> {
                    return ftr.format(d.getDate()).equals(String.format("%04d%02d", year, month));
                }).map(d -> {
                    Category category =
                            getCategoryMap().get(d.getType().getValue().getValue())
                                    .stream()
                                    .filter(c -> d.getCategory().getValue().equals(c.getValue()))
                                    .findFirst().get();

                    return d.setCategory(category);
                }).sorted((d1, d2) ->

                {
                    return (d2.getDate() + d2.getId()).compareTo((d1.getDate() + d1.getId()));
                }).collect(Collectors.toList());
    }

    /**
     * @param user
     * @param id
     * @return
     */
    public DetailDto findById(String user, String id) {
        return map.get(user).get(id);
    }

    /**
     * @param user
     * @param dto
     * @return
     */
    public DetailDto add(String user, DetailDto dto) {
        DetailDto idDto = DetailDto.create(uuid.getNewId(), dto);
        map.get(user).put(idDto.getId(), idDto);
        return idDto;
    }

    /**
     * @param user
     * @param dto
     * @return
     */
    public DetailDto mod(String user, DetailDto dto) {

        map.get(user).put(dto.getId(), dto);

        return dto;
    }

    public SummaryDto getSummary(String user, int year, int month) {

        List<DetailDto> details = findByYearMonth(user, year, month);
        if (details.size() == 0) {
            return null;
        }

        long incomeSum = details.stream().filter(d -> d.getType().getValue() == TypeDto.INCOME.getValue())
                .mapToLong(d -> d.getAmount()).sum();
        long expenseSum = details.stream().filter(d -> d.getType().getValue() == TypeDto.EXPENSE.getValue())
                .mapToLong(d -> d.getAmount()).sum();
        double percentage = ((double) expenseSum) / ((double) incomeSum);

        return SummaryDto.of(expenseSum, incomeSum - expenseSum, (int) (percentage * 100d));
    }

    public Map<String, List<Category>> getCategoryMap() {
        Map<String, List<Category>> map = new HashMap<>();

        map.put("1", Arrays.asList(
                Category.of("1", "収入A"),
                Category.of("2", "収入B"),
                Category.of("3", "収入C")));

        map.put("2", Arrays.asList(
                Category.of("4", "支出A"),
                Category.of("5", "支出B"),
                Category.of("6", "支出C"),
                Category.of("7", "支出D"),
                Category.of("8", "支出E"),
                Category.of("9", "支出F")));

        return map;
    }
}
