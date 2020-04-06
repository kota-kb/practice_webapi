package practice.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 明細DTO
 */
public class DetailDto {

    /**
     * 区分列挙型
     */
    public static enum Type {

        NONE(""), INCOME("1"), EXPENSE("2");

        private final String cd;

        Type(String cd) {
            this.cd = cd;
        }

        @JsonValue
        public String getValue() {
            return cd;
        }

        /**
         * 変換
         *
         * @param cd
         * @return
         */
        @JsonCreator
        public static Type of(String cd) {
            if (cd != null) {
                for (Type v : values()) {
                    if (v.getValue() != null && v.getValue().equals(cd)) {
                        return v;
                    }
                }
            }
            return NONE;
        }

    }

    /**
     * 区分DTO
     */
    public static class TypeDto {

        public static TypeDto INCOME = new TypeDto(Type.INCOME, "収入");
        public static TypeDto EXPENSE = new TypeDto(Type.EXPENSE, "支出");

        private Type value;
        private String name;

        private TypeDto() {
        }

        private TypeDto(Type value, String name) {
            this.value = value;
            this.name = name;
        }

        public Type getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static TypeDto of(Type type, String name) {
            return new TypeDto(type, name);
        }

        @Override
        public String toString() {
            return String.format("{value:%s, name:%s}", value, name);
        }

    }

    /**
     * カテゴリDTO
     */
    public static class Category {
        private String value;
        private String name;

        private Category() {
        }

        private Category(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static Category of(String cd, String name) {
            return new Category(cd, name);
        }

        @Override
        public String toString() {
            return String.format("{value:%s, name:%s}", value, name);
        }
    }

    // 明細ID
    private String id;
    // 日付
    private LocalDate date;
    // 区分
    private TypeDto type;
    // カテゴリ
    private Category category;
    // 金額
    private Long amount;
    // メモ
    private String memo;

    /**
     * 明細DTOファクトリ
     *
     * @param id
     * @param date
     * @param type
     * @param category
     * @param amount
     * @param memo
     * @return
     */
    public static DetailDto create(String id, LocalDate date, TypeDto type, Category category, Long amount, String memo) {
        DetailDto dto = new DetailDto();
        dto.id = id;
        dto.date = date;
        dto.type = type;
        dto.category = category;
        dto.amount = amount;
        dto.memo = memo;
        return dto;
    }

    public static DetailDto create(String id, DetailDto dto) {
        dto.id = id;
        return dto;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public TypeDto getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public DetailDto setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }

    @Override
    public String toString() {
        return String.format("{id:%s, date:%s, type:%s, category:%s, amount:%s, memo:%s}", id, date, type, category, amount, memo);
    }

}
