import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface BoardHeartbeat extends Record<string, JsonSerializable> {
	state: number;
	internalTime: number;
	className: `${ClassNamePrefix}.BoardHeartbeat`;
}
