import { JsonSerializable } from '@wuespace/telestion-client-types';

export interface WidgetProps extends Record<string, JsonSerializable> {
	title: string;
}
