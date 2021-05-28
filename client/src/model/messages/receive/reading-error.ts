import { JsonSerializable } from '@wuespace/telestion-client-types';
import { ClassNamePrefix } from '../../channels';

export interface ReadingError extends Record<string, JsonSerializable> {
	type: string;
	message: string;
	id: number;
	time: number;
	className: `${ClassNamePrefix}.ReadingError`;
}
