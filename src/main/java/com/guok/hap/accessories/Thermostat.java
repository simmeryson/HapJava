package com.guok.hap.accessories;

import com.guok.hap.accessories.thermostat.*;

/**
 * A thermostat with heating and cooling controls.
 *
 * @author Andy Lintner
 * @deprecated Use {@link BasicThermostat}, {@link HeatingThermostat}, and {@link CoolingThermostat} instead
 */
@Deprecated
public interface Thermostat extends BasicThermostat, HeatingThermostat, CoolingThermostat {

	

}
