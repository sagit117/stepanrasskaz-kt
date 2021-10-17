'use strict'

export function goRoute(path: string) {
    if (!path || typeof path !== "string") return

    const loc = document.location

    if (loc.pathname !== path) loc.assign(path)
}