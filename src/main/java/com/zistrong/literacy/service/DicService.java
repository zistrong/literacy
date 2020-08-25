package com.zistrong.literacy.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zistrong.literacy.entity.Charactor;

@Service
public class DicService extends BaseNativeSqlRepository {

    @Autowired
    CharactorService charactorService;
    private Map<String, String> map = new ConcurrentHashMap<>();

    private boolean showFavorite = false;

    public void switchs() {
        this.showFavorite = !this.showFavorite;
    }

    @Modifying
    @Transactional
    public Charactor getCurrentCharactor() {
        this.intVoiceDic();
        int i = this.currentIndex();
        List<Charactor> charactors;
        if (this.showFavorite) {
            charactors = charactorService.findFavorite();
        } else {
            charactors = charactorService.find();
        }
        if(charactors.isEmpty()) {
            return null;
        }

        Charactor nowCharactor = charactors.get(i % charactors.size());

        String voice = map.get(nowCharactor.getCharString());
        if (voice.contains(",")) {
            voice = voice.split("\\,")[0];
        }
        i++;
        nowCharactor.setVoice(voice);
        this.charactorService.updateIndex(i % charactors.size());
        return nowCharactor;
    }

    private void intVoiceDic() {
        synchronized (this) {
            if (map.isEmpty()) {
                List<Object[]> dics = this.findAllDic();
                for (Object[] dic : dics) {
                    map.put(dic[0].toString(), dic[1].toString());
                }
            }
        }

    }

    public List<Object[]> findAllDic() {
        String sqlString = "select * from dic";
        List<Object[]> objecArraytList = sqlNativeArrayList(sqlString);
        return objecArraytList;
    }

    public int currentIndex() {
        String sqlString = "select count from currentChar where id=1";
        return (int) sqlSingleNativeArrayList(sqlString);
    }

    @Modifying
    @Transactional
    public void updateIndex(int count) {
        String sqlString = "update currentChar set count= " + count + " where id= 1";
        exeSqlNative(sqlString);
    }
}
