export interface IAppLookup {
  id?: number;
  lookupCode?: string | null;
  displayValue?: string | null;
  sequence?: number | null;
  category?: string | null;
  dependentCode?: string | null;
}

export const defaultValue: Readonly<IAppLookup> = {};
