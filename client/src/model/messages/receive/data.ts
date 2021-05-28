import { JsonSerializable } from '@wuespace/telestion-client-types';
import { Matrix3D, Vector3D } from '../../utils';
import { ClassNamePrefix } from '../../channels';

export interface Data extends Record<string, JsonSerializable> {
	accel: Vector3D;
	mag: Vector3D;
	gyro: Vector3D;
	temp: number;
	rotMatrix: Matrix3D;
	rpyAccelMag: Vector3D;
	rpyGyro: Vector3D;
	rpyCombined: Vector3D;
	className: `${ClassNamePrefix}.Data`;
}
