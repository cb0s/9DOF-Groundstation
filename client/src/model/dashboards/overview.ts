import { Dashboard } from '@wuespace/telestion-client-types';

export const Overview: Dashboard = {
	title: 'Overview',
	columns: 12,
	rows: 12,
	widgets: [
		{
			id: 'overview-1',
			widgetName: 'dataWidget',
			width: 3,
			height: 12,
			initialProps: {
				title: 'Data Widget'
			}
		},
		{
			id: 'overview-2',
			widgetName: 'sampleWidget',
			width: 9,
			height: 4
		},
		{
			id: 'overview-3',
			widgetName: 'sampleWidget',
			width: 9,
			height: 4
		},
		{
			id: 'overview-4',
			widgetName: 'sampleWidget',
			width: 9,
			height: 4
		}
	]
};
