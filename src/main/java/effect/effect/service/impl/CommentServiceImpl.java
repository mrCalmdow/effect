package effect.effect.service.impl;

import effect.effect.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @author feilongchen
 * @create 2018-02-10 1:03 PM
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public boolean isValidContent(String content) {
        //TODO add validate for contents
        return true;
    }
}
