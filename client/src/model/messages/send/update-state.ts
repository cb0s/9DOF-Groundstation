import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface UpdateState extends Record<string, JsonSerializable> {
	state: number;
	className: `${ClassNamePrefix}.UpdateState`;
}
