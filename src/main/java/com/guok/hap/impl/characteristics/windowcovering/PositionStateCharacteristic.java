package com.guok.hap.impl.characteristics.windowcovering;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;


/**
 * the state of the position of accessories. This characteristic can be used with doors,
 * windows, awnings, or window coverings.
 * <p>
 * <P> Valid Values
 * <p> 0 Going to minimum
 * <p> 1 Going to maximum
 * <p> 2 Stopped
 *
 * @author guok
 */
public class PositionStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //    private final WindowCovering windowCovering;
    public static final String UUID = "00000072-0000-1000-8000-0026BB765291";

    public PositionStateCharacteristic() {
        this(null);
    }

    public PositionStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "The position state", 2);

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Read only
        return HapStatusCodes.READ_OLNY;
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        this.subcribeCallback = callback;
    }

    @Override
    public void unsubscribe() {
        this.subcribeCallback = null;
    }

}
