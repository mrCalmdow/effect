package effect.effect.common.vo;

import effect.effect.common.constants.CommonConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * @author feilongchen
 * @create 2018-01-29 9:52 PM
 */
public class PageableVO implements Pageable {

    private int pageNumber = CommonConstants.DEFAULT_PAGE_NUMBER;
    private int pageSize = CommonConstants.DEFAULT_PAGE_SIZE;
    private String direction;
    private String property;
    private int totalPages;
    private long total;


    public int getTotalPages() {
        return this.totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getOffset() {
        return this.pageNumber * this.pageSize;
    }

    @Override
    public Sort getSort() {
        if (StringUtils.hasText(direction) && StringUtils.hasText(property)) {
            return new Sort(Sort.Direction.fromString(this.direction), property);
        }
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
