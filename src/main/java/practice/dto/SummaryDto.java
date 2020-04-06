package practice.dto;

/**
 * サマリ情報DTO
 */
public class SummaryDto {

    private Long expense;
    private Long balance;
    private Integer percentage;

    private SummaryDto() {
    }

    public static SummaryDto of(Long expense, Long balance, Integer percentage) {
        SummaryDto dto = new SummaryDto();
        dto.expense = expense;
        dto.balance = balance;
        dto.percentage = percentage;

        return dto;
    }

    public Long getExpense() {
        return expense;
    }

    public Long getBalance() {
        return balance;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public String toString() {
        return String.format("{expense:%s, balance:%s, percentage:%s}", expense, balance, percentage);
    }
}
