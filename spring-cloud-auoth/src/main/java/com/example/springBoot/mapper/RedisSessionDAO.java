package com.example.springBoot.mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.example.springBoot.config.RedisClient;

@Component
public class RedisSessionDAO extends AbstractSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
	@Autowired
	private RedisClient redisClient;


    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "session:";


    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }


    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{
        logger.info("----saveSession---"+session.getId());
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }


        byte[] key = getByteKey(session.getId());
        byte[] value = SerializationUtils.serialize(session);
       // session.setTimeout(redisManager.getExpire()*1000);
        
     this.redisClient.set(key, value, Integer.parseInt(session.getTimeout()/1000+""));

    }


    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        redisClient.del(this.getByteKey(session.getId()));
        logger.info("----delete Session---"+session.getId());


    }


    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();


        Set<byte[]> keys = redisClient.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = (Session)SerializationUtils.deserialize(redisClient.get(key));
                sessions.add(s);
            }
        }


        return sessions;
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }


        Session s = (Session)SerializationUtils.deserialize(redisClient.get(this.getByteKey(sessionId)));
        return s;
    }


    /**
     * 获得byte[]型的key
     * @param sessionId
     * @return
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }




    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}