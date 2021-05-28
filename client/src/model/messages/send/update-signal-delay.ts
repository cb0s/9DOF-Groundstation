import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface UpdateSignalDelay extends Record<string, JsonSerializable> {
	delay: number;
	className: `${ClassNamePrefix}.UpdateSignalDelay`;
}
