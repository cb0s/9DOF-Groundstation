import { useState } from 'react';
import { useBroadcast } from '@wuespace/telestion-client-core';
import { ConnectionDetails } from '../../model/messages';
import { UpdateCom } from '../../model/channels';
import { Form } from '@adobe/react-spectrum';

export interface DetailsProps {
	details: ConnectionDetails;
}

export function Details({ details }: DetailsProps) {
	const [device, setDevice] = useState(details.com);
	const [baudrate, setBaudrate] = useState(details.baudRate);

	const broadcast = useBroadcast(UpdateCom);

	return (
		<Form maxWidth="100%" isRequired>
		{/* Picker is missing for available coms and baudrate  */}
		</Form>
	);
}
