export interface IAppTopicLookup {
  id?: number;
  topicCode?: string | null;
  topicName?: string | null;
  targetGroup?: string | null;
}

export const defaultValue: Readonly<IAppTopicLookup> = {};
