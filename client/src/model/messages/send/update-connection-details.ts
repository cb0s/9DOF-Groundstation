import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface UpdateConnectionDetails
	extends Record<string, JsonSerializable> {
	device: string;
	baudrate: number;
	className: `${ClassNamePrefix}.UpdateConnectionDetails`;
}
