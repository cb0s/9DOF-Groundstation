import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface UpdateTelemetryDelay extends Record<string, JsonSerializable> {
	delay: number;
	className: `${ClassNamePrefix}.UpdateTelemetryDelay`;
}
