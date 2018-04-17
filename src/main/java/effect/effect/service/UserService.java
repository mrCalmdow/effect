package effect.effect.service;

import effect.effect.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-01-23 11:08 PM
 */
public interface UserService {

    boolean isAccountNotExists(String account);
    boolean isAccountExists(String account);
    User getUserByAccount(String Account);
    <T,E> List<T> transformUid2Account(List<T> sources, Class<E> clazz) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException;
    <T,E> List<T> transformUid2Nickname(List<T> sources, Class<E> clazz) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException;
    <T, E> List<T> transformCommitUserId2Account(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;
    <T, E> List<T> transformCommitUserId2Nickname(List<T> sources, Class<E> clazz) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;


}
