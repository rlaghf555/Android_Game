package com.example.pocketball.MyFrameWork;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.pocketball.R;

import java.util.HashMap;

public class SoundManager {
    private static SoundManager s_Instance;
    private SoundPool m_SoundPool;          //안드로이드에서 지원하는 사운드풀
    public MediaPlayer m_Background;
    private HashMap m_SoundPoolMap;         //불러온 사운드의 아이디 값을 저장할 해시맵
    private AudioManager m_AudioManager;    //원하는 대로 사운드를 관리할 수 있는 오디오 매니저
    private Context m_Activity;             //애플리케이션의 컨텍스트 값
    public static SoundManager getInstance(){
        if(s_Instance==null){
            s_Instance = new SoundManager();
        }
        return s_Instance;
    }
    public void Init(Context _context){
        SoundPool.Builder m_SoundPoolBuilder;
        m_SoundPoolBuilder = new SoundPool.Builder();
        m_SoundPoolBuilder.setMaxStreams(5);
        m_SoundPool = m_SoundPoolBuilder.build();
        //m_SoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        m_SoundPoolMap = new HashMap();
        m_AudioManager = (AudioManager) _context.getSystemService(Context.AUDIO_SERVICE);
        m_Activity = _context;
        m_Background = MediaPlayer.create(m_Activity, R.raw.background_sound2);
        m_Background.setLooping(true);
    }
    public void addSound(int _index, int _soundID){
        int id = m_SoundPool.load(m_Activity, _soundID, 1);//사운드 풀 로드
        m_SoundPoolMap.put(_index, id);//해시맵에 아이디 값을 받앙노 인덱스로 저장
    }
    public void play(int _index) {  //재생
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume /
                m_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_SoundPool.play((Integer) m_SoundPoolMap.get(_index),
                streamVolume, streamVolume,
                1, 0, 1f);
    }
    public void play(int _index, float left, float right) {  //재생
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume /
                m_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_SoundPool.play((Integer) m_SoundPoolMap.get(_index),
                streamVolume*left, streamVolume*right,
                1, 0, 1f);
    }
    public void playLooped(int _index){//반복재생
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume /
                m_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_SoundPool.play((Integer) m_SoundPoolMap.get(_index),
                streamVolume, streamVolume,
                1, -1, 1f);
    }
}
