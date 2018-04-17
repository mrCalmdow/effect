package effect.effect.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * @author feilongchen
 * @create 2018-02-10 9:19 PM
 */
public interface CommonService {

    Map wappingPageProperties(Long offset, Integer limit);
    PageRequest generatePageRequest(Long offset, Integer limit);
    <T,E> List<E> batchCopyProperties(List<T> sources, Class<E> targets) throws IllegalAccessException, InstantiationException;
}
