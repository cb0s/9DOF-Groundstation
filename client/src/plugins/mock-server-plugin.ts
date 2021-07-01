import chalk from 'chalk';
import { Logger, ChalkLogger } from '@fliegwerk/logsemts';
import { MockServer, OnClose, OnInit } from '@wuespace/vertx-mock-server';

import { FakeDataGenerator } from '../lib/fake-data-generator';
import { Data } from '../model/messages';

class RocketSoundMockServer extends MockServer implements OnInit, OnClose {
	dataInterval: any;

	readonly dataChannel = 'data';
	readonly fakeChannel2 = 'aggregated-imu.acc.y';
	readonly fakeChannel3 = 'aggregated-imu.acc.z';

	readonly fakeDataXGen: FakeDataGenerator;
	readonly fakeDataYGen: FakeDataGenerator;
	readonly fakeDataZGen: FakeDataGenerator;

	constructor(...args: any[]) {
		super(...args);
		this.fakeDataXGen = new FakeDataGenerator(0, 10, 10);
		this.fakeDataYGen = new FakeDataGenerator(-2, 12, 20);
		this.fakeDataZGen = new FakeDataGenerator(4, 16, 7);
	}

	onInit() {
		this.dataInterval = setInterval(() => {
			const x = this.fakeDataXGen.getDataSample().avg;
			const y = this.fakeDataYGen.getDataSample().avg;
			const z = this.fakeDataZGen.getDataSample().avg;

			const message: Data = {
				accel: [x, y, z],
				mag: [x, y, z],
				gyro: [x, y, z],
				temp: 0,
				rotMatrix: [
					[x, y, z],
					[x, y, z],
					[x, y, z]
				],
				rpyAccelMag: [0, 0, 0],
				rpyGyro: [0, 0, 0],
				rpyCombined: [0, 0, 0],
				className: 'com.github.cb0s.gs9dof.messages.Data'
			};

			this.send(this.dataChannel, message);
		}, 33); // send every 1 second new data
	}

	onClose() {
		clearInterval(this.dataInterval);
	}
}

const logger = new Logger({
	loggers: [ChalkLogger(chalk)]
});

export function onReady() {
	if (
		process.env.NODE_ENV !== 'production' &&
		process.env.MOCK_SERVER === 'true'
	) {
		const server = new RocketSoundMockServer({
			logger: logger.getComponentLogger('Mock Server')
		});
		server.listen({ port: 9870, hostname: '0.0.0.0' });
	}
}
