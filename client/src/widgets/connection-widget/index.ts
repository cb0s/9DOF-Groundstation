import { Widget } from "@wuespace/telestion-client-types";
import { Widget as WidgetRenderer } from "./widget";

export const widget: Widget = {
	name: 'connectionWidget',
	title: 'Connection Widget',
	version: '0.1.0',
	Widget: WidgetRenderer
};
