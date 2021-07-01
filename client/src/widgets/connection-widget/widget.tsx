import { Divider, Flex, Heading, View } from '@adobe/react-spectrum';
import { BaseRendererProps } from '@wuespace/telestion-client-types';
import { WidgetProps } from './model';
import { useChannel, useRequest } from '@wuespace/telestion-client-core';
import { AvailableComs } from '../../model/channels';
import { useEffect, useState } from 'react';
import { ConnectionDetails, RequestComs } from '../../model/messages';
import { LoadingIndicator } from '@wuespace/telestion-client-common';
import { Details } from './details';

const requestMessage: RequestComs = {
	className: 'com.github.cb0s.gs9dof.messages.RequestComs'
};

export function Widget({ title }: BaseRendererProps<WidgetProps>) {
	const [details, setDetails] = useState<ConnectionDetails>();
	// handle updates on details
	useChannel<ConnectionDetails>(AvailableComs, setDetails);

	// explicitly request details on initial render
	const requestDetails = useRequest<ConnectionDetails>(AvailableComs);
	useEffect(() => {
		requestDetails(requestMessage, setDetails);
	}, [requestDetails]);

	return (
		<View padding="size-200">
			<Flex direction="column">
				<Heading margin={0} level={3}>
					{title}
				</Heading>
				<Divider size="M" marginTop="size-100" />
				{/* @ts-ignore */}
				<LoadingIndicator dependencies={[details]}>
					{details => <Details details={details} />}
				</LoadingIndicator>
			</Flex>
		</View>
	);
}
