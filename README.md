**Risk-Scoring Service**

**Overview**
- **Purpose:** Consume payment events, evaluate fraud/risk using simple rules, and publish a risk-assessed event.
- **Consumes topic:** `payment.created`
- **Produces topic:** `payment.risk.assessed`

**PaymentCreatedEvent (input)**
- **Description:** Event published when a payment is created.

- **Example JSON payload:**

```json
{
	"orderId": "order_789",
	"amount": 5123.45,
	"customerId": "cust_456",
	"paymentMethod": "card",
	"cardNumber": "4111111111111111",
	"cardExpiry": "12/2026",
	"cardCvv": "123",
	"status": "CREATED",
	"description": "Purchase of item X",
	"transactionId": "txn_987",
	"billingAddress": "123 Main St, City, Country",
	"shippingAddress": "123 Main St, City, Country",
	"customerEmail": "user@example.com",
	"customerPhone": "+5511999999999",
	"authorizationCode": "auth_555",
	"eventId": "evt_111"
}
```

**Implemented Rules (🧠)**
- **High amount rule:** If `amount > 5000` → increase score / mark as high risk.
- **Velocity rule:** More than 5 transactions from the same `customerId` within 1 minute → suspicious.
- **Blacklist rule:** If `customerId` is present in the configured blacklist → mark as fraud.
- **Card country mismatch:** If IP geolocation country ≠ card country → high risk.
- **Night transactions:** Transactions between `00:00–04:00` (local time) → increase risk.

Each rule adds a `reason` and contributes to a numerical `riskScore`.

**Output event: `payment.risk.assessed`**
- **Description:** Produced after analysis; contains risk score, level and rule details.
- **Example payload:**

```json
{
	"paymentId": "pay_123",
	"customerId": "cust_456",
	"riskScore": 87,
	"riskLevel": "HIGH",
	"reasons": [
		"HIGH_AMOUNT",
		"CARD_COUNTRY_MISMATCH",
		"NIGHT_TRANSACTION"
	],
	"ruleResults": {
		"high_amount": {"matched": true, "detail": "amount=5123.45"},
		"velocity": {"matched": false, "count": 1},
		"blacklist": {"matched": false},
		"card_country_mismatch": {"matched": true, "ip_country": "BR", "card_country": "US"},
		"night_transaction": {"matched": true, "timestamp": "2026-02-15T02:23:10Z"}
	},
	"analyzedAt": "2026-02-15T02:23:11Z"
}
```

Risk score is an integer (0-100). `riskLevel` is derived from thresholds (example: 0-29 LOW, 30-59 MEDIUM, 60-84 HIGH, 85-100 FRAUD), tuneable.

**Configuration & Environment**
- **Broker:** Configure `BROKER_URL` for your message broker (Kafka, RabbitMQ, etc.).
- **Topics:** `INPUT_TOPIC` (default `payment.created`) and `OUTPUT_TOPIC` (default `payment.risk.assessed`).
- **Blacklist source:** configure `BLACKLIST_URL` or local store for blacklisted `customerId`s.
- **GeoIP provider:** configure `GEOIP_API_URL` or local DB for IP→country lookups.

**Running & Testing**
- To test locally, publish a `payment.created` event to your broker with the example payload.
- The service will consume, evaluate rules, and emit a `payment.risk.assessed` event.

Quick test (conceptual):

```bash
# publish example JSON to your broker's `payment.created` topic
# e.g., kafka-console-producer --topic payment.created --broker-list localhost:9092 < example.json
```

**Extending rules**
- Add new rule functions that return `{matched: bool, scoreDelta: int, reason: string, detail?: {}}`.
- Compose rule results into a final `riskScore` and `reasons` array.

**Observability**
- Emit metrics: total processed, matched-rules counts, average `riskScore`.
- Log analyzed events with `paymentId` and `riskLevel` for audit.

**Notes**
- Topic names are in English: input `payment.created`, output `payment.risk.assessed`.
- Keep sensitivity and thresholds configurable; review false positives periodically.

See [README.md](README.md) for this file.