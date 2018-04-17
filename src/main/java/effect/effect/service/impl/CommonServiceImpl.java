package effect.effect.service.impl;

import effect.effect.common.constants.CommonConstants;
import effect.effect.service.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author feilongchen
 * @create 2018-02-10 9:24 PM
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public Map wappingPageProperties(Long offset, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        offset = ((null == offset) || (0 >= offset)) ? 0 : offset;
        limit = ((null == limit) || (0 >= limit)) ? CommonConstants.DEFAULT_PAGE_SIZE : limit;
        map.put("offset", offset);
        map.put("limit", limit);
        return map;    }

    @Override
    public PageRequest generatePageRequest(Long offset, Integer limit) {
        Map pagePropertiesMap = wappingPageProperties(offset, limit);
        offset = (Long) pagePropertiesMap.get(CommonConstants.PAGE_PROPERTIES_OFFSET);
        limit = (Integer) pagePropertiesMap.get(CommonConstants.PAGE_PROPERTIES_LIMIT);
        return new PageRequest(offset.intValue()/limit.intValue(), limit);
    }

    /**
     * batch copy beans properties
     * @param sources
     * @param target
     * @param <T>
     * @param <E>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public  <T,E> List<E> batchCopyProperties(List<T> sources, Class<E> target) throws IllegalAccessException, InstantiationException {
        if(CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<E> targets = new ArrayList<>();
        for(T source : sources) {
            E e = target.newInstance();
            BeanUtils.copyProperties(source, e);
            targets.add(e);
        }
        return targets;
    }
}
