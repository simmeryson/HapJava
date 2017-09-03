package com.guok.event;

import android.text.TextUtils;

/**
 * @author guok
 */

public class NlpEventType {

    public static MusicOpen MusicOpen(EventType eventType) {
        return new MusicOpen(eventType);
    }

    public static VideoOpen VideoOpen(EventType eventType) {
        return new VideoOpen(eventType);
    }

    public static FMOpen FMOpen(EventType eventType) {
        return new FMOpen(eventType);
    }

    public static MapOpen MapOpen(EventType eventType) {
        return new MapOpen(eventType);
    }

    public static NewsOpen NewsOpen(EventType eventType) {
        return new NewsOpen(eventType);
    }

    public static MallOpen MallOpen(EventType eventType) {
        return new MallOpen(eventType);
    }

    public static DrawerOpen DrawerOpen(EventType eventType) {
        return new DrawerOpen(eventType);
    }
    public static BeautyOpen BeautyOpen(EventType eventType) {
        return new BeautyOpen(eventType);
    }

    public static HealthOpen HealthOpen(EventType eventType) {
        return new HealthOpen(eventType);
    }

    public static BathRoomOpen BathRoomOpen(EventType eventType) {
        return new BathRoomOpen(eventType);
    }

    public static HotWaterOpen HotWaterOpen(EventType eventType) {
        return new HotWaterOpen(eventType);
    }

    public static AllClose AllClose(EventType eventType) {
        return new AllClose(eventType);
    }

    public static String MusicLastListKey() {
        return new MusicLastList().getTarget();
    }

    public static MusicLastList MusicLastList(EventType eventType) {
        return new MusicLastList(eventType);
    }

    public static MusicList MusicList(EventType eventType) {
        return new MusicList(eventType);
    }

    public static String MusicLocalListKey() {
        return new MusicLocalList().getTarget();
    }

    public static MusicLocalList MusicLocalList(EventType eventType) {
        return new MusicLocalList(eventType);
    }

    public static String VideoListKey() {
        return new VideoList().getTarget();
    }

    public static VideoList VideoList(EventType eventType) {
        return new VideoList(eventType);
    }

    public static String MapListKey() {
        return new MapList().getTarget();
    }

    public static MapList MapList(EventType eventType) {
        return new MapList(eventType);
    }

    public static String FMListKey() {
        return new FMList().getTarget();
    }

    public static FMList FMList(EventType eventType) {
        return new FMList(eventType);
    }

    public static String GeneralPageKey() {
        return new GeneralPage().getTarget();
    }

    public static GeneralPage GeneralPageStep(EventType eventType) {
        return new GeneralPage(eventType);
    }

    public static String PlayerControllerKey() {
        return new PlayerController().getTarget();
    }

    public static PlayerController PlayerController(EventType eventType) {
        return new PlayerController(eventType);
    }

    public static String PlayerNextKey() {
        return new PlayerNext().getTarget();
    }

    public static PlayerNext PlayerNext(EventType eventType) {
        return new PlayerNext(eventType);
    }

    public static String VolumeAdjustorKey() {
        return new VolumeAdjustor().getTarget();
    }

    public static VolumeAdjustor VolumeAdjustor(EventType eventType) {
        return new VolumeAdjustor(eventType);
    }

    public static String LyricKey() {
        return new Lyric().getTarget();
    }

    public static Lyric Lyric(EventType eventType) {
        return new Lyric(eventType);
    }

    public static AIGoBack AIGoBack(EventType eventType) {
        return new AIGoBack(eventType);
    }

    public static String WindowControlKey() {
        return new WindowControl().getTarget();
    }

    public static WindowControl WindowControl(EventType eventType) {
        return new WindowControl(eventType);
    }

    public static String MaxScreenKey() {
        return new MaxScreen().getTarget();
    }

    public static MaxScreen MaxScreen(EventType eventType) {
        return new MaxScreen(eventType);
    }

    public static BlueTooth BlueTooth(EventType eventType) {
        return new BlueTooth(eventType);
    }

    public static DeviceInfo DeviceInfo(EventType eventType) {
        return new DeviceInfo(eventType);
    }

    public static Heater Heater(EventType eventType) {
        return new Heater(eventType);
    }

    public static Lamp Lamp(EventType eventType) {
        return new Lamp(eventType);
    }

    public static Fan Fan(EventType eventType) {
        return new Fan(eventType);
    }

    public static BathHeater BathHeater(EventType eventType) {
        return new BathHeater(eventType);
    }

    public static BathRoom BathRoom(EventType eventType) {
        return new BathRoom(eventType);
    }

    public static Hotwater Hotwater(EventType eventType) {
        return new Hotwater(eventType);
    }

    public static WeatherOpen WeatherOpen(EventType eventType) {
        return new WeatherOpen(eventType);
    }
    /**
     * 各种模块，打开/关闭
     */
    public static abstract class ModelOpen extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_OPEN = "open";
        public static final String VALUE_CLOSE = "close";

        public ModelOpen(String target, String object) {
            super(target, object);
        }

        private ModelOpen(EventType eventType) {
            super(eventType);
        }

        public String openClose() {
            String value = getValue(this, KEY_OPS);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 打开、关闭 音乐
     */
    public static class MusicOpen extends ModelOpen {
        public static final String VALUE_AUTO = "auto";

        public MusicOpen() {
            super("witch_mirror", "sys_music");
        }

        private MusicOpen(EventType eventType) {
            super(eventType);
        }


        public static String key() {
            return new MusicOpen().getTarget();
        }
    }

    /**
     * 打开、关闭 视频
     */
    public static class VideoOpen extends ModelOpen {

        public VideoOpen() {
            super("witch_mirror", "sys_video");
        }

        private VideoOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new VideoOpen().getTarget();
        }
    }

    /**
     * 打开、关闭 广播
     */
    public static class FMOpen extends ModelOpen {

        public FMOpen() {
            super("witch_mirror", "sys_broadcast");
        }

        private FMOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new FMOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭地图
     */
    public static class MapOpen extends ModelOpen {

        public MapOpen() {
            super("witch_mirror", "sys_map");
        }

        private MapOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new MapOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭新闻
     */
    public static class NewsOpen extends ModelOpen {

        public NewsOpen() {
            super("witch_mirror", "sys_journalism");
        }

        private NewsOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new NewsOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭抽屉
     */
    public static class DrawerOpen extends ModelOpen {

        public DrawerOpen() {
            super("witch_mirror", "sys_drawer");
        }

        private DrawerOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new DrawerOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭商城
     */
    public static class MallOpen extends ModelOpen {

        public MallOpen() {
            super("witch_mirror", "sys_mall");
        }

        private MallOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new MallOpen().getTarget();
        }
    }
    /**
     * 打开, 关闭美妆
     */
    public static class BeautyOpen extends ModelOpen {

        public BeautyOpen() {
            super("witch_mirror", "sys_beauty");
        }

        private BeautyOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new BeautyOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭健康档案
     */
    public static class HealthOpen extends ModelOpen {

        public HealthOpen() {
            super("witch_mirror", "sys_health");
        }

        private HealthOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new HealthOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭浴室环境
     */
    public static class BathRoomOpen extends ModelOpen {

        public BathRoomOpen() {
            super("witch_mirror", "sys_envbath");
        }

        private BathRoomOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new BathRoomOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭智慧热水
     */
    public static class HotWaterOpen extends ModelOpen {

        public HotWaterOpen() {
            super("witch_mirror", "sys_hotwater");
        }

        private HotWaterOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new HotWaterOpen().getTarget();
        }
    }

    /**
     * 打开, 关闭天气
     */
    public static class WeatherOpen extends ModelOpen {

        public WeatherOpen() {
            super("witch_mirror", "sys_weather");
        }

        private WeatherOpen(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new WeatherOpen().getTarget();
        }
    }

    /**
     * 关闭 全部窗口
     */
    public static class AllClose extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_CLOSE = "close";

        public AllClose() {
            super("witch_mirror", "sys_all");
        }

        private AllClose(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new AllClose().getTarget();
        }

        public boolean closeAllWindow() {
            String value = getValue(this, KEY_OPS);
            return !(TextUtils.isEmpty(value) && value.equals(VALUE_CLOSE));
        }
    }

    /**
     * 最近播放
     */
    @Deprecated
    public static class MusicLastList extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_OPEN = "打开";

        private MusicLastList() {
            super("music", "music_last_list");
        }

        private MusicLastList(EventType eventType) {
            super(eventType);
        }

        public String openEvent() {
            String value = getValue(this, KEY_OPS);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 热歌榜, 新歌榜, 飙升榜, 最新播放, 本地音乐
     */
    public static class MusicList extends EventType {
        public static final String KEY_NAME = "name";
        public static final String VALUE_HOT = "hot";
        public static final String VALUE_NEW = "new";
        public static final String VALUE_RAISE = "soaring";
        public static final String VALUE_LATEST = "lately";
        public static final String VALUE_LOCAL = "local";

        public MusicList() {
            super("witch_mirror", "sys_music_list");
        }

        private MusicList(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new MusicList().getTarget();
        }

        public String openList() {
            String value = getValue(this, KEY_NAME);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }

    /**
     * 本地音乐
     */
    @Deprecated
    public static class MusicLocalList extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_OPEN = "打开";

        private MusicLocalList() {
            super("music", "music_local_list");
        }

        private MusicLocalList(EventType eventType) {
            super(eventType);
        }

        public String openEvent() {
            String value = getValue(this, KEY_OPS);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 资讯，电视剧，电影，综艺，动漫，汽车，教育，体育
     */
    public static class VideoList extends EventType {
        public static final String KEY_NAME = "name";
        public static final String VALUE_INFO = "information";
        public static final String VALUE_TV = "tv";
        public static final String VALUE_FILM = "film";
        public static final String VALUE_VARIETY = "variety";
        public static final String VALUE_COMIC = "comic";
        public static final String VALUE_CAR = "auto";
        public static final String VALUE_EDU = "edu";
        public static final String VALUE_SPORT = "sport";

        public VideoList() {
            super("witch_mirror", "sys_video_list");
        }

        private VideoList(EventType eventType) {
            super(eventType);
        }

        public String openList() {
            String value = getValue(this, KEY_NAME);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }

    /**
     * FM 列表。热门，有声书，相声，评书，儿童，情感，历史
     */
    public static class FMList extends EventType {
        public static final String KEY_NAME = "name";
        public static final String VALUE_HOT = "hot";
        public static final String VALUE_AUDIOBOOK = "audiobook";
        public static final String VALUE_RADIO = "radio_station";
        public static final String VALUE_STORYTELLING = "storytelling";
        public static final String VALUE_CHILDREN = "children";
        public static final String VALUE_EMOTION = "emotion";
        public static final String VALUE_HISTORY = "history";

        public FMList() {
            super("witch_mirror", "sys_broadcast_list");
        }

        private FMList(EventType eventType) {
            super(eventType);
        }

        public String openList() {
            String value = getValue(this, KEY_NAME);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }

    /**
     * 地图列表。路线设定 驾车路线 公交路线 步行路线 骑行路线
     */
    public static class MapList extends EventType {
        public static final String KEY_NAME = "name";
        public static final String VALUE_SETLINES = "setlines";
        public static final String VALUE_DRIVE = "drive";
        public static final String VALUE_TRANSPORTATION = "transportation";
        public static final String VALUE_WALK = "walk";
        public static final String VALUE_RIDING = "riding";


        public MapList() {
            super("witch_mirror", "sys_map_list");
        }

        private MapList(EventType eventType) {
            super(eventType);
        }

        public String openList() {
            String value = getValue(this, KEY_NAME);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }

    /**
     * 上一页，下一页，第几页
     */
    public static class GeneralPage extends EventType {
        public static final String KEY_FLAP = "flap";
        public static final String VALUE_NEXT = "next";
        public static final String VALUE_PREV = "pre";
        public static final String VALUE_HOME = "homepage";
        public static final String KEY_PAGE = "num";

        GeneralPage() {
            super("witch_mirror", "sys_pages");
        }

        private GeneralPage(EventType eventType) {
            super(eventType);
        }

        public String pageNext() {
            String value = getValue(this, KEY_FLAP);
            return !TextUtils.isEmpty(value) ? value : "";
        }

        public String pageIndex() {
            String value = getValue(this, KEY_PAGE);
            return !TextUtils.isEmpty(value) ? value : "";
        }

    }

    /**
     * 暂停，停止，继续播放
     */
    public static class PlayerController extends EventType {
        public static final String KEY_CMD = "control";
        public static final String VALUE_PAUSE = "pause";
        public static final String VALUE_PLAY = "play";
        public static final String VALUE_FORWARD = "forward";
        public static final String VALUE_BACK = "back";

        private PlayerController() {
            super("player", "com_control");
        }

        private PlayerController(EventType eventType) {
            super(eventType);
        }

        public String cmd() {
            String value = getValue(this, KEY_CMD);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 上一首，上一集，第几首
     */
    public static class PlayerNext extends EventType {
        public static final String KEY_TRUN = "truns";
        public static final String KEY_INDEX = "num";
        public static final String VALUE_NEXT = "next";
        public static final String VALUE_PREV = "pre";

        public PlayerNext() {
            super("player", "com_turns");
        }

        private PlayerNext(EventType eventType) {
            super(eventType);
        }

        public String playNext() {
            String value = getValue(this, KEY_TRUN);
            return !TextUtils.isEmpty(value) ? value : "";
        }

        public String playIndex() {
            String value = getValue(this, KEY_INDEX);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }


    /**
     * 音量增大，减小
     */
    public static class VolumeAdjustor extends EventType {
        public static final String KEY_ADJ = "adj";
        public static final String VALUE_ADD = "add";
        public static final String VALUE_REDUCE = "reduce";
        public static final String VALUE_MAX = "vmax";
        public static final String VALUE_MUTE = "vmin";
        public static final String VALUE_CANCLE = "vmin_cancle";

        public VolumeAdjustor() {
            super("witch_mirror", "sys_voice");
        }

        private VolumeAdjustor(EventType eventType) {
            super(eventType);
        }

        public String adjust() {
            String value = getValue(this, KEY_ADJ);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 歌词
     */
    public static class Lyric extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_OPEN = "open";
        public static final String VALUE_CLOSE = "close";

        public Lyric() {
            super("witch_mirror", "sys_music_words");
        }

        private Lyric(EventType eventType) {
            super(eventType);
        }

        public String openClose() {
            String value = getValue(this, KEY_OPS);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 小优回去，魔镜回去，优宝回去
     */
    public static class AIGoBack extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_BACK = "back";

        public AIGoBack() {
            super("witch_mirror", "go_back");
        }

        private AIGoBack(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new AIGoBack().getTarget();
        }

        public boolean back() {
            String value = getValue(this, KEY_OPS);
            return !TextUtils.isEmpty(value) && value.equals(VALUE_BACK);
        }
    }

    public static class WindowControl extends EventType {
        public static final String KEY_CON = "con";
        public static final String VALUE_MAX = "wmax";
        public static final String VALUE_RESTORE = "restore";
        public static final String VALUE_CLOSE = "close";
        public static final String VALUE_REFRESH = "refresh";
        public static final String VALUE_CONFIRM = "conform";
        public static final String VALUE_SAVE = "save";
        public static final String VALUE_CANCEL = "cancel";
        public static final String VALUE_ADD = "add";
        public static final String VALUE_SWITCH = "switch";
        public static final String VALUE_RETURN = "return";
        public static final String VALUE_CLEAN = "clean";
        public static final String VALUE_REPUT = "reput";

        public WindowControl() {
            super("witch_mirror", "sys_control");
        }

        private WindowControl(EventType eventType) {
            super(eventType);
        }

        public String getValue() {
            return getValue(this, KEY_CON);
        }


        public Boolean clean() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_CLEAN:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean reput() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_REPUT:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean maxScreen() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_MAX:
                    return true;
                case VALUE_RESTORE:
                    return false;
                default:
                    return null;
            }
        }

        public Boolean closeWindow() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_CLOSE:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean refresh() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_REFRESH:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean confirm() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_CONFIRM:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean save() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_SAVE:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean cancle() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_CANCEL:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean add() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_ADD:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean switchWindow() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_SWITCH:
                    return true;
                default:
                    return null;
            }
        }

        public Boolean backWindow() {
            switch (getValue(this, KEY_CON)) {
                case VALUE_RETURN:
                    return true;
                default:
                    return null;
            }
        }
    }

    /**
     * 窗口最大化，全屏， 退出全屏
     */
    @Deprecated
    public static class MaxScreen extends EventType {
        public static final String KEY_MODE = "screen_mode";
        public static final String VALUE_MAX = "全屏";
        public static final String VALUE_QUIT = "退出全屏";

        public MaxScreen() {
            super("general", "max_screen");
        }

        private MaxScreen(EventType eventType) {
            super(eventType);
        }

        public String maxScreen() {
            String value = getValue(this, KEY_MODE);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**
     * 打开蓝牙，关闭蓝牙
     */
    public static class BlueTooth extends ModelOpen {

        public BlueTooth() {
            super("witch_mirror", "sys_bluetooth");
        }

        private BlueTooth(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new BlueTooth().getTarget();
        }
    }

    /**
     * 设备信息
     */
    public static class DeviceInfo extends EventType {
        public static final String KEY_QUERY = "query";
        public static final String VALUE_UPDATE = "update";
        public static final String VALUE_DEVICE = "device";
        public static final String VALUE_HELP = "help";

        public DeviceInfo() {
            super("witch_mirror", "sys_query");
        }

        private DeviceInfo(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new DeviceInfo().getTarget();
        }

        public String versionView() {
            String value = getValue(this, KEY_QUERY);
            return TextUtils.isEmpty(value) ? "" : value;
        }
    }

    /**************************设备控制*********************************/

    public static abstract class DeviceControl extends EventType {
        public static final String KEY_OPS = "ops";
        public static final String VALUE_OPEN = "open";
        public static final String VALUE_CLOSE = "close";
        public static final String RES_OPEN = "已打开";
        public static final String RES_CLOSE = "已关闭";

        public DeviceControl(String target, String object) {
            super(target, object);
        }

        private DeviceControl(EventType eventType) {
            super(eventType);
        }

        public Boolean openClose() {
            switch (getValue(this, KEY_OPS)) {
                case VALUE_OPEN:
                    return true;
                case VALUE_CLOSE:
                    return false;
                default:
                    return null;
            }
        }
    }

    /**
     * 打开热水器，关闭热水器
     */
    public static class Heater extends DeviceControl {


        public Heater() {
            super("device_control", "hot_ops");
        }

        private Heater(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new Heater().getTarget();
        }
    }

    /**
     * 打开灯
     */
    public static class Lamp extends DeviceControl {

        public Lamp() {
            super("device_control", "light_ops");
        }

        private Lamp(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new Lamp().getTarget();
        }

    }

    /**
     * 打开风扇
     */
    public static class Fan extends DeviceControl {

        public Fan() {
            super("device_control", "fan_ops");
        }

        private Fan(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new Fan().getTarget();
        }
    }

    /**
     * 打开浴霸
     */
    public static class BathHeater extends DeviceControl {

        public BathHeater() {
            super("device_control", " bath_heater");
        }

        private BathHeater(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new BathHeater().getTarget();
        }
    }

    public static class BathRoom extends EventType {
        public static final String KEY_QUERY = "query";
        public static final String VALUE_AIR = "atmosphere";
        public static final String VALUE_TEMP = "temperature";
        public static final String VALUE_HUMIDITY = "humidity";

        public BathRoom() {
            super("witch_mirror", "device_query");
        }

        public BathRoom(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new BathRoom().getTarget();
        }

        public String query() {
            String value = getValue(this, KEY_QUERY);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }

    public static class Hotwater extends EventType {
        public static final String KEY_ADJ = "adj";

        public Hotwater() {
            super("witch_mirror", "hot_adj");
        }

        public Hotwater(EventType eventType) {
            super(eventType);
        }

        public static String key() {
            return new Hotwater().getTarget();
        }

        public String query() {
            String value = getValue(this, KEY_ADJ);
            return !TextUtils.isEmpty(value) ? value : "";
        }
    }
}

