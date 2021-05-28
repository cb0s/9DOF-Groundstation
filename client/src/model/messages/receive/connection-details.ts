import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface ConnectionDetails extends Record<string, JsonSerializable> {
	com: string;
	baudRate: number;
	connected: boolean;
	className: `${ClassNamePrefix}.ConnectionDetails`;
}
