import { UserConfig } from '@wuespace/telestion-client-types';
import { Overview } from './dashboards/overview';

export const userConfig: UserConfig = {
	admin: {
		dashboards: [Overview]
	}
};
