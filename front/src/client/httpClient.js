import axios from "axios";

export default () => {
	const baseUrl = "https://oil-api.kovalev.team";

	return axios.create({
		baseURL: baseUrl,
		headers: {
			"Content-Type": "application/json"
		}
	});
};