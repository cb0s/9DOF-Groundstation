import { JsonSerializable } from '@wuespace/telestion-client-types';
import { isObj } from './utils';

/**
 * A key string which defines how an extractor function extracts a deeply nested value from
 */
export type ExtractDefinition = string;

/**
 * Extracts a deeply nested value from a given object.
 * @param obj - the deeply nested object
 * @returns the value of the specified key string in the object.
 *
 * @throws TypeError - when the key string cannot be found
 * in the object structure
 * @throws TypeError - when the selected path in the object does not point
 * to a numeric value
 */
export type ExtractValue = (obj: JsonSerializable) => number;

/**
 * Builds a function which extracts a deeply nested value from a given object
 * using the specified key string.
 *
 * The key string must have a special accessor format,
 * where `.` and `[var]` are access operators
 * to get a property value from an object or array.
 *
 * @param definition - the key string to access the deeply nested value
 * @param unsafe - disable parsing checks and parse only (defaults to `false`)
 * @returns the a function which extracts a deeply nested value
 * from a given object
 *
 * @example
 * ```ts
 * const obj = {
 * 	acc: [
 * 		{
 * 			x: 0,
 * 			y: 1,
 * 			z: 2
 * 		},
 * 		{
 * 			x: 3,
 * 			y: 4,
 * 			z: 5
 * 		}
 * 	],
 * 	gyro: {
 * 		x: 6,
 * 		y: 7,
 * 		z: 8
 * 	},
 * 	temp: 18.7
 * };
 *
 * console.log(extractValue(obj, 'acc[0].x')); // 0
 * console.log(extractValue(obj, 'acc[0].y')); // 1
 * console.log(extractValue(obj, 'gyro.z')); // 8
 * console.log(extractValue(obj, 'temp')); // 18.7
 * ```
 */
export function valueExtractor(
	definition: ExtractDefinition,
	unsafe = false
): ExtractValue {
	// replace [$1] with .$1 and split on .
	const accessors = definition.replace(/\[(\w+)\]/g, '.$1').split('.');

	if (unsafe) {
		// ignore everything and PARSE!!!!
		// @ts-ignore
		return obj => {
			// @ts-ignore
			const value = accessors.reduce((partial, accessor) => {
				// @ts-ignore
				return partial[accessor];
			}, obj);

			if (typeof value !== 'number') {
				throw new TypeError(
					`Invalid value received. (access expected: number, received: ${JSON.stringify(
						value
					)}`
				);
			}

			return value;
		};
	}

	return obj => {
		const value = accessors.reduce((partial, accessor, i) => {
			// check if current partial object
			if (!isObj(partial)) {
				throw new TypeError(
					`Invalid message body received. (iteration: ${i}, accessor: ${
						accessors[i]
					}, expected: object, received: ${JSON.stringify(partial)}`
				);
			}
			// extract new partial object from current partial object
			const newPartial = partial[accessor];
			// check if not undefined
			if (typeof newPartial === 'undefined') {
				throw new TypeError(`Accessor ${accessors[i]} is undefined.`);
			}

			return newPartial;
		}, obj);

		if (typeof value !== 'number') {
			throw new TypeError(
				`Invalid value received. (access expected: number, received: ${JSON.stringify(
					value
				)}`
			);
		}

		return value;
	};
}
