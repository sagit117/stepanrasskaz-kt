'use strict';
export function goRoute(path) {
    if (!path || typeof path !== "string")
        return;
    const loc = document.location;
    if (loc.pathname !== path)
        loc.assign(path);
}
//# sourceMappingURL=utils.js.map