export interface IAppTopicType {
  id?: number;
  topicCode?: string | null;
  topicName?: string | null;
  targetGroup?: string | null;
}

export const defaultValue: Readonly<IAppTopicType> = {};
