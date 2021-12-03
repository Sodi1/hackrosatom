export function constructQueryString(queryMap) {
	const queryParams = new URLSearchParams();

	Object.entries(queryMap).forEach(([key, value]) => {
		if (value === null || value === undefined) {
			return;
		}

		if (Array.isArray(value)) {
			value.forEach(param => queryParams.append(key, param));
		} else if (value instanceof Date) {
			queryParams.append(key, value.toJSON());
		} else {
			queryParams.append(key, value);
		}
	})

	return queryParams.toString();
}

export function constructUrlWithQueryParams(url, queryMap) {
	return `${url}?${constructQueryString(queryMap)}`
}
