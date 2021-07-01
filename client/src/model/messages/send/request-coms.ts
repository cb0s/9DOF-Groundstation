import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface RequestComs extends Record<string, JsonSerializable> {
	className: `${ClassNamePrefix}.RequestComs`;
}
