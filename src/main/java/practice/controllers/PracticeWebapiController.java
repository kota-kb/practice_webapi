package practice.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import practice.dto.DetailDto;
import practice.dto.DetailDto.Category;
import practice.dto.SummaryDto;
import practice.services.DetailsService;
import practice.services.SummaryService;

@RestController
@RequestMapping("/api")
public class PracticeWebapiController {

    static final Logger logger = LoggerFactory.getLogger(PracticeWebapiController.class);

    /**
     * 明細処理サービス
     */
    @Autowired
    DetailsService detailsService;

    /**
     * サマリサービス
     */
    @Autowired
    SummaryService summaryService;

    /**
     * サマリ情報取得
     *
     * @param pYear 対象年
     * @param pMonth 対象月
     * @param res Http response object
     * @return
     */
    @GetMapping("/summary")
    public SummaryDto getSummary(
            @RequestParam(defaultValue = "", name = "year") String pYear,
            @RequestParam(defaultValue = "", name = "month") String pMonth,
            HttpServletResponse res) {

        if (pYear.isEmpty() || pMonth.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Optional<SummaryDto> opt = summaryService.findSummaryByYearMonth(Integer.valueOf(pYear),
                Integer.valueOf(pMonth));
        if (!opt.isPresent()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return opt.get();
    }

    /**
     * 一覧取得
     *
     * @param pYear 対象年
     * @param pMonth 対象月
     * @param res Http response object
     * @return
     */
    @GetMapping("/details")
    public List<DetailDto> getDetailsByYearMonth(
            @RequestParam(defaultValue = "", name = "year") String pYear,
            @RequestParam(defaultValue = "", name = "month") String pMonth,
            HttpServletResponse res) {

        if (pYear.isEmpty() || pMonth.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        List<DetailDto> details = detailsService.findDetailsByYearMonth(Integer.valueOf(pYear),
                Integer.valueOf(pMonth));

        return details;
    }

    /**
     * ID指定明細取得
     *
     * @param id 明細ID
     * @param res Http response object
     * @return
     */
    @GetMapping("/details/{id}")
    public DetailDto getDetailById(
            @PathVariable("id") String id,
            HttpServletResponse res) {

        Optional<DetailDto> opt = detailsService.findDetailById(id);
        if (!opt.isPresent()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return opt.get();
    }

    /**
     * 新規明細追加
     *
     * @param dto 明細情報
     * @param res Http response object
     * @return
     */
    @PostMapping("/details")
    public DetailDto postNewDetail(
            @RequestBody DetailDto dto,
            HttpServletResponse res) {

        // validation

        return detailsService.addDetail(dto);
    }

    /**
     * 既存明細修正
     *
     * @param id 明細ID
     * @param dto 明細情報
     * @param res http response object
     * @return
     */
    @PutMapping("/details/{id}")
    public DetailDto putDetailById(
            @PathVariable("id") String id,
            @RequestBody DetailDto dto,
            HttpServletResponse res) {

        Optional<DetailDto> opt = detailsService.findDetailById(id);
        if (!opt.isPresent()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return detailsService.updateDetail(dto);
    }

    /**
     * @return
     */
    @GetMapping("/categories")
    public Map<String, List<Category>> getCategories() {
        return detailsService.getCategoryMap();
    }

}
