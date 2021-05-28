import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';
import { Vector3D } from '../../utils';

export interface CalibrationData extends Record<string, JsonSerializable> {
	accelOffset: Vector3D;
	minMagOffset: Vector3D;
	maxMagOffset: Vector3D;
	gyroOffset: Vector3D;
	className: `${ClassNamePrefix}.CalibrationData`;
}
