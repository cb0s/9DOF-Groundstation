import { Flex, Heading, Text } from '@adobe/react-spectrum';
import {
	Cell,
	Column,
	Row,
	TableBody,
	TableHeader,
	TableView
} from '@react-spectrum/table';
import { EventBusState, useEventBus } from '@wuespace/telestion-client-core';
import { StateSelector } from 'zustand';
import { useEffect, useRef } from 'react';
import { Data as DataMessage } from '../../model/messages';
import { Data } from '../../model/channels';

const precision = 3;

const selector: StateSelector<EventBusState, EventBusState['eventBus']> =
	state => state.eventBus;

export function DataRender() {
	const eventBus = useEventBus(selector);
	const accelXRef = useRef<HTMLDivElement>(null);
	const accelYRef = useRef<HTMLDivElement>(null);
	const accelZRef = useRef<HTMLDivElement>(null);

	const magXRef = useRef<HTMLDivElement>(null);
	const magYRef = useRef<HTMLDivElement>(null);
	const magZRef = useRef<HTMLDivElement>(null);

	const gyroXRef = useRef<HTMLDivElement>(null);
	const gyroYRef = useRef<HTMLDivElement>(null);
	const gyroZRef = useRef<HTMLDivElement>(null);

	const tempRef = useRef<HTMLDivElement>(null);

	const rot11Ref = useRef<HTMLDivElement>(null);
	const rot12Ref = useRef<HTMLDivElement>(null);
	const rot13Ref = useRef<HTMLDivElement>(null);
	const rot21Ref = useRef<HTMLDivElement>(null);
	const rot22Ref = useRef<HTMLDivElement>(null);
	const rot23Ref = useRef<HTMLDivElement>(null);
	const rot31Ref = useRef<HTMLDivElement>(null);
	const rot32Ref = useRef<HTMLDivElement>(null);
	const rot33Ref = useRef<HTMLDivElement>(null);

	const rollAMRef = useRef<HTMLDivElement>(null);
	const pitchAMRef = useRef<HTMLDivElement>(null);
	const yawAMRef = useRef<HTMLDivElement>(null);

	const rollGRef = useRef<HTMLDivElement>(null);
	const pitchGRef = useRef<HTMLDivElement>(null);
	const yawGRef = useRef<HTMLDivElement>(null);

	const rollCRef = useRef<HTMLDivElement>(null);
	const pitchCRef = useRef<HTMLDivElement>(null);
	const yawCRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const handler = ({
			accel,
			mag,
			gyro,
			temp,
			rotMatrix,
			rpyAccelMag,
			rpyGyro,
			rpyCombined
		}: DataMessage) => {
			if (accelXRef.current)
				accelXRef.current.innerText = `${accel[0].toFixed(precision)}`;
			if (accelYRef.current)
				accelYRef.current.innerText = `${accel[1].toFixed(precision)}`;
			if (accelZRef.current)
				accelZRef.current.innerText = `${accel[2].toFixed(precision)}`;

			if (magXRef.current)
				magXRef.current.innerText = `${mag[0].toFixed(precision)}`;
			if (magYRef.current)
				magYRef.current.innerText = `${mag[1].toFixed(precision)}`;
			if (magZRef.current)
				magZRef.current.innerText = `${mag[2].toFixed(precision)}`;

			if (gyroXRef.current)
				gyroXRef.current.innerText = `${gyro[0].toFixed(precision)}`;
			if (gyroYRef.current)
				gyroYRef.current.innerText = `${gyro[1].toFixed(precision)}`;
			if (gyroZRef.current)
				gyroZRef.current.innerText = `${gyro[2].toFixed(precision)}`;

			if (tempRef.current) tempRef.current.innerText = `${temp.toFixed(2)}°C`;

			if (rot11Ref.current)
				rot11Ref.current.innerText = `${rotMatrix[0][0].toFixed(precision)}`;
			if (rot12Ref.current)
				rot12Ref.current.innerText = `${rotMatrix[0][1].toFixed(precision)}`;
			if (rot13Ref.current)
				rot13Ref.current.innerText = `${rotMatrix[0][2].toFixed(precision)}`;

			if (rot21Ref.current)
				rot21Ref.current.innerText = `${rotMatrix[1][0].toFixed(precision)}`;
			if (rot22Ref.current)
				rot22Ref.current.innerText = `${rotMatrix[1][1].toFixed(precision)}`;
			if (rot23Ref.current)
				rot23Ref.current.innerText = `${rotMatrix[1][2].toFixed(precision)}`;

			if (rot31Ref.current)
				rot31Ref.current.innerText = `${rotMatrix[2][0].toFixed(precision)}`;
			if (rot32Ref.current)
				rot32Ref.current.innerText = `${rotMatrix[2][1].toFixed(precision)}`;
			if (rot33Ref.current)
				rot33Ref.current.innerText = `${rotMatrix[2][2].toFixed(precision)}`;

			if (rollAMRef.current)
				rollAMRef.current.innerText = `${rpyAccelMag[0].toFixed(precision)}°`;
			if (pitchAMRef.current)
				pitchAMRef.current.innerText = `${rpyAccelMag[1].toFixed(precision)}°`;
			if (yawAMRef.current)
				yawAMRef.current.innerText = `${rpyAccelMag[2].toFixed(precision)}°`;

			if (rollGRef.current)
				rollGRef.current.innerText = `${rpyGyro[0].toFixed(precision)}°`;
			if (pitchGRef.current)
				pitchGRef.current.innerText = `${rpyGyro[1].toFixed(precision)}°`;
			if (yawGRef.current)
				yawGRef.current.innerText = `${rpyGyro[2].toFixed(precision)}°`;

			if (rollCRef.current)
				rollCRef.current.innerText = `${rpyCombined[0].toFixed(precision)}°`;
			if (pitchCRef.current)
				pitchCRef.current.innerText = `${rpyCombined[1].toFixed(precision)}°`;
			if (yawCRef.current)
				yawCRef.current.innerText = `${rpyCombined[2].toFixed(precision)}°`;
		};

		eventBus?.register(Data, handler);
		return () => eventBus?.unregister(Data, handler);
	}, [eventBus]);

	return (
		<Flex direction="column" gap="size-200">
			<Heading margin={0} level={5}>
				Raw Values
			</Heading>
			<TableView aria-label="Accelerometer data" maxWidth="100%">
				<TableHeader>
					<Column>Device</Column>
					<Column align="end">X</Column>
					<Column align="end">Y</Column>
					<Column align="end">Z</Column>
				</TableHeader>
				<TableBody>
					<Row>
						<Cell>Accelerometer</Cell>
						<Cell>
							<div ref={accelXRef}>-</div>
						</Cell>
						<Cell>
							<div ref={accelYRef}>-</div>
						</Cell>
						<Cell>
							<div ref={accelZRef}>-</div>
						</Cell>
					</Row>
					<Row>
						<Cell>Magnetometer</Cell>
						<Cell>
							<div ref={magXRef}>-</div>
						</Cell>
						<Cell>
							<div ref={magYRef}>-</div>
						</Cell>
						<Cell>
							<div ref={magZRef}>-</div>
						</Cell>
					</Row>
					<Row>
						<Cell>Gyroscope</Cell>
						<Cell>
							<div ref={gyroXRef}>-</div>
						</Cell>
						<Cell>
							<div ref={gyroYRef}>-</div>
						</Cell>
						<Cell>
							<div ref={gyroZRef}>-</div>
						</Cell>
					</Row>
				</TableBody>
			</TableView>

			<Heading margin={0} level={5}>
				Temperature
			</Heading>
			<div ref={tempRef}>-°C</div>

			<Heading margin={0} level={5}>
				Rotation Matrix
			</Heading>
			<TableView>
				<TableHeader>
					<Column align="center">X</Column>
					<Column align="center">Y</Column>
					<Column align="center">Z</Column>
				</TableHeader>
				<TableBody>
					<Row>
						<Cell>
							<div ref={rot11Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot12Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot13Ref}>-</div>
						</Cell>
					</Row>
					<Row>
						<Cell>
							<div ref={rot21Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot22Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot23Ref}>-</div>
						</Cell>
					</Row>
					<Row>
						<Cell>
							<div ref={rot31Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot32Ref}>-</div>
						</Cell>
						<Cell>
							<div ref={rot33Ref}>-</div>
						</Cell>
					</Row>
				</TableBody>
			</TableView>

			<Heading margin={0} level={5}>
				Euler Rotations
			</Heading>
			<TableView>
				<TableHeader>
					<Column>Source</Column>
					<Column align="end">Roll</Column>
					<Column align="end">Pitch</Column>
					<Column align="end">Yaw</Column>
				</TableHeader>
				<TableBody>
					<Row>
						<Cell>A+M</Cell>
						<Cell>
							<div ref={rollAMRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={pitchAMRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={yawAMRef}>-°</div>
						</Cell>
					</Row>
					<Row>
						<Cell>G</Cell>
						<Cell>
							<div ref={rollGRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={pitchGRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={yawGRef}>-°</div>
						</Cell>
					</Row>
					<Row>
						<Cell>Combined</Cell>
						<Cell>
							<div ref={rollCRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={pitchCRef}>-°</div>
						</Cell>
						<Cell>
							<div ref={yawCRef}>-°</div>
						</Cell>
					</Row>
				</TableBody>
			</TableView>
		</Flex>
	);
}
