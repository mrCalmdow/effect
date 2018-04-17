package effect.effect.service.impl;

import effect.effect.po.User;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-01-23 11:09 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserService.class);


    private static final String ACCOUNT = "account";
    private static final String NICKNAME = "nickname";

    @Autowired
    private UserAutoRepo userAutoRepo;

    @Override
    public boolean isAccountNotExists(String account) {
        return !isAccountExists(account);
    }

    @Override
    public boolean isAccountExists(String account) {
        return null == userAutoRepo.findByAccount(account);
    }

    @Override
    public User getUserByAccount(String account) {
        User user = userAutoRepo.findByAccount(account);
        if(null == user) {
            log.info("User not exists: account = " +  account);
            return null;
        }
        return user;
    }

    @Override
    public <T, E> List<T> transformUid2Account(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return transformUid2Identifying(sources, clazz, ACCOUNT);
    }

    @Override
    public <T, E> List<T> transformUid2Nickname(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return transformUid2Identifying(sources, clazz, NICKNAME);
    }

    @Override
    public <T, E> List<T> transformCommitUserId2Account(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return transformCommitUserId2Identifying(sources, clazz, ACCOUNT);
    }

    @Override
    public <T, E> List<T> transformCommitUserId2Nickname(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return transformCommitUserId2Identifying(sources, clazz, NICKNAME);
    }

    /**
     *
     * @param sources
     * @param clazz
     * @param identifying
     * @param <T>
     * @param <E>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private  <T,E> List<T> transformUid2Identifying(List<T> sources,Class<E> clazz, String identifying) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        if(CollectionUtils.isEmpty(sources)) {
            return sources;
        }
        for(T t : sources) {
            Method getUid = clazz.getMethod("getUid");
            Method setUid = clazz.getMethod("setUid", String.class);
            String uid = (String) getUid.invoke(t);
            User user = userAutoRepo.findOne(uid);
            if(ACCOUNT.equals(identifying)) {
                setUid.invoke(t, user.getAccount());
            } else if(NICKNAME.equals(identifying)) {
                setUid.invoke(t, user.getNickname());
            }
        }
        return sources;
    }

    /**
     *
     * @param sources
     * @param clazz
     * @param identifying
     * @param <T>
     * @param <E>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public <T, E> List<T> transformCommitUserId2Identifying(List<T> sources, Class<E> clazz, String identifying) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        if(CollectionUtils.isEmpty(sources)) {
            return sources;
        }
        for(T t : sources) {
            Method getCommitUserId = clazz.getMethod("getCommitUserId");
            Method setCommitUserId = clazz.getMethod("setCommitUserId", String.class);
            User user = userAutoRepo.findOne((String) getCommitUserId.invoke(t));
            if(ACCOUNT.equals(identifying)) {
                setCommitUserId.invoke(t, user.getAccount());
            } else if(NICKNAME.equals(identifying)) {
                setCommitUserId.invoke(t, user.getNickname());

            }
        }
        return sources;
    }


}
