# sorrentino

Sorrentino is a Twitch Event Sub integration for Minecraft servers.

For now I'm just trying to do as much as possible for serving both a working plugin and a useful library for other
developers as fast as I can.

For now I won't add every event, for now just most important for a streamer like:

- [Stream Online](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#streamonline) - Triggered whenever a
  channel starts a stream.
- [Stream Offline](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#streamoffline) - Triggered whenever
  a channel ends a stream.
- [Channel Cheer](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelcheer) - Triggered whenever a
  user uses bits on a specific channel.
- [Channel Follow](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelfollow) - Triggered whenever
  a user start to follow a specific channel;
- [Channel Subscribe](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelsubscribe) - Triggered
  whenever a user subscribes to a specific channel;
- [Channel Subscription End](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelsubscriptionend) -
  Triggered whenever a user' subscription ends;
- [Channel Subscription Gift](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelsubscriptiongift) -
  Triggered whenever a user gift a subscription;
- [Channel Points Custom Reward Redemption Add](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelchannel_points_automatic_reward_redemptionadd) -
  Triggered whenever a custom point reward has been redeemed from a user.
- 
Obviously you can always extend the library and add more events, just pr and I'll be happy to review it.

## Next
This is what I'll work on next:
 - [ ] Add more events.
 - [ ] Drop Twitch4J & create custom lightweight HTTP client.

