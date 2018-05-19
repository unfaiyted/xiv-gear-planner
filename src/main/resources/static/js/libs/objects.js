
// Take JSON data and represent as JSON object for input
module.exports = {

    settings: {

    },

    toPlayerObj: (json) => {
        return {
            name: json.data.name,
            gender: (json.data.gender === "male") ? 0 : 1,
            avatar: json.data.avatar,
            server: json.data.server,
            portrait: json.data.portrait,
            loadstoneId: json.lodestone_id

        };
    }


};