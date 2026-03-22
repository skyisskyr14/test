import { chromium } from 'playwright'

const url = process.argv[2] || 'http://127.0.0.1:5173'
const output = process.argv[3] || '../output/latest-screenshot.png'

const browser = await chromium.launch({ headless: true })
const context = await browser.newContext({ viewport: { width: 1440, height: 900 }, ignoreHTTPSErrors: true })

if (process.env.SEED_TOKEN && process.env.SEED_USER) {
  const token = process.env.SEED_TOKEN
  const user = process.env.SEED_USER
  await context.addInitScript(({ seededToken, seededUser }) => {
    localStorage.setItem('token', seededToken)
    localStorage.setItem('user', seededUser)
  }, { seededToken: token, seededUser: user })
}

const page = await context.newPage()
await page.goto(url, { waitUntil: 'networkidle', timeout: 60000 })
await page.screenshot({ path: output, fullPage: true })
await context.close()
await browser.close()
console.log(`Screenshot saved to ${output}`)
