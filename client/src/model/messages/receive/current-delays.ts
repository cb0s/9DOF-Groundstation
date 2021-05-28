import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface CurrentDelays extends Record<string, JsonSerializable> {
	signalDelay: number;
	telemetryDelay: number;
	className: `${ClassNamePrefix}.CurrentDetails`;
}
